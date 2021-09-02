class AerospikeTestSuite
    extends IntegrationTestsBase
    with AerospikeTestContainer {
     
      "Aerospike client" should "do write/read" taggedAs (IntegrationTest) in {
        import com.aerospike.client.Key
        import com.aerospike.client.Bin

        // Write single value.
        val key = new Key("testing", "myset", "mykey")
        val bin = new Bin("mybin", "myvalue")
        client.put(writePolicy, key, bin)
        val record = client.get(batchPolicy, key)

        assert(record.bins.get("mybin") === "myvalue")
      }
    }
