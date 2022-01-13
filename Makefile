all:
	rm -f target/*
	javac -h target/ -d target/ src/*.java
	g++ -shared -fPIC -Itarget/ -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux src/Test.cpp -o target/libtest.so
	java -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -XX:ShenandoahGCHeuristics=aggressive -Djava.library.path=target/ -cp target Test native
	java -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -XX:ShenandoahGCHeuristics=aggressive -Djava.library.path=target/ -cp target Test java
	java -Djava.library.path=target/ -cp target Test native
	java -Djava.library.path=target/ -cp target Test java

