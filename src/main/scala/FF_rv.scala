import chisel3._
import chisel3.util.Decoupled

class FF_rv(dataWidth: Int = 32) extends Module {

  val io = IO(new Bundle {
    val din   = Flipped(Decoupled(UInt(dataWidth.W)))
    val dout  = Decoupled(UInt(dataWidth.W))
  })
  
  val data_0, data_1 = RegInit(0.U(dataWidth.W))
  val valid_0, valid_1, areg = RegInit(false.B)
  val vaux = Mux(areg, valid_0, valid_1)

  io.din.ready := areg
  io.dout.bits := Mux(areg, data_0, data_1)
  io.dout.valid := vaux

  areg := io.dout.ready || !vaux

  when(areg) {
    data_0  := io.din.bits
    data_1  := data_0
    valid_0 := io.din.valid
    valid_1 := valid_0
  }
}

/**
 * An object extending App to generate the Verilog code.
 */
object FF_rv extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new FF_rv(dataWidth = 32))
}
