import com.aerospike.client.AerospikeClient
import com.aerospike.client.Host
import com.aerospike.client.async.EventPolicy
import com.aerospike.client.async.NioEventLoops
import com.aerospike.client.policy.BatchPolicy
import com.aerospike.client.policy.ClientPolicy
import com.aerospike.client.policy.WritePolicy
import com.dimafeng.testcontainers.ForAllTestContainer
import com.dimafeng.testcontainers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait

trait AerospikeTestContainer { self: ForAllTestContainer =>

  override val container: GenericContainer = GenericContainer(
    "aerospike/aerospike-server:5.6.0.4",
    exposedPorts = Seq(3000, 3001, 3002),
    env = Map(
      "NAMESPACE"    -> "testing",
      "SERVICE_PORT" -> "3000"
    ),
    waitStrategy = Wait.forLogMessage(".*migrations: complete.*", 1)
  )

  val writePolicy = new WritePolicy()
  val batchPolicy = new BatchPolicy()

  lazy val ip = container.containerIpAddress

  lazy val aerospikeHosts = Host.parseHosts(
    s"$ip:${container.mappedPort(3000)},$ip:${container.mappedPort(3001)},$ip:${container.mappedPort(3002)}",
    3000
  )


  lazy val client = {
    val eventLoops = new NioEventLoops(new EventPolicy(), 8)
    val clientPolicy = {
      val clientPolicy = new ClientPolicy()
      clientPolicy.eventLoops         = eventLoops
      clientPolicy.maxConnsPerNode    = 50
      clientPolicy.batchPolicyDefault = batchPolicy
      clientPolicy.writePolicyDefault = new WritePolicy()
      clientPolicy
    }

    new AerospikeClient(clientPolicy, aerospikeHosts: _*)
  }
}