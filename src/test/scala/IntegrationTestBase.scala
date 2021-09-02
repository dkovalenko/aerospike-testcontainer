import org.scalatest
import org.scalatest._
import flatspec._
import com.dimafeng.testcontainers.ForAllTestContainer

abstract class IntegrationTestsBase
    extends AnyFlatSpec
    with OptionValues
    with Inside
    with Inspectors
    with ForAllTestContainer

object IntegrationTest extends scalatest.Tag("IntegrationTest")