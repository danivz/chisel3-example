waveform:
	sbt test && gtkwave -a FF_rv_signals.gtkw test_run_dir/FF_rv_should_pass/FF_rv.vcd

verilog:
	sbt run

clean:
	rm -R test_run_dir/
	rm *.v
	rm *.fir
	rm *.anno.json