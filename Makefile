.PHONY: all clean

all: Main.class

Main.class: $(wildcard *.java)
	javac *.java

clean:
	rm -f *~ *.class
