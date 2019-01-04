
class_d=bin
source_d=src

JAVAC=javac
JFLAGS=-g -d $(class_d) -sourcepath $(source_d) -Xlint:all
JVM=java

classpath:=$(class_d)

ifneq ($(CLASSPATH),)
	classpath:= $(CLASSPATH):$(classpath)
endif

JVFLAGS=-cp $(classpath)
export CLASSPATH:=$(classpath)

.SUFFIXES: .java .class
.PHONY: all run clean

$(class_d)/OptimalTreeLabeling.class: $(source_d)/*.java
	@$(JAVAC) $(JFLAGS) $(source_d)/*.java

$(class_d):
	mkdir $(class_d)

all: $(class_d)/OptimalTreeLabeling.class

run:
	@$(JVM) $(JVFLAGS) OptimalTreeLabeling $LABELING

clean:
	@rm -rf $(class_d)/*
