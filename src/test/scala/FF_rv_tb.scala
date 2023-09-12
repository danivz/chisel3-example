import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class FF_rv_TestBench extends AnyFlatSpec with ChiselScalatestTester{
  behavior of "FF_rv"

  it should "pass" in {
    test(new FF_rv(dataWidth = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      // Disable clock timeout for long simulations (default 1000 clock cycles)
      dut.clock.setTimeout(0)

      // Initial values
      dut.io.din.bits.poke(0.U)
      dut.io.din.valid.poke(false.B)
      dut.io.dout.ready.poke(true.B)
      dut.clock.step(10)

      // Test values
      for(i <- 1 to 20) {
        dut.io.din.bits.poke(i.U)
        dut.io.din.valid.poke(true.B)
        if(i % 2 == 0) {
          dut.io.dout.ready.poke(true.B)
        } else {
          dut.io.dout.ready.poke(false.B)
        }
        dut.clock.step()
      }
      dut.clock.step(20)
    }
  }
}
