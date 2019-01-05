
class_d=bin
source_d=src
latex_d=latex

JAVAC=javac
JFLAGS=-g -d $(class_d) -sourcepath $(source_d) -Xlint:all
JVM=java

SHELL := bash

RUN=./run.sh

RM=readme.text
PDF=report.pdf

classpath:=$(class_d)

ifneq ($(CLASSPATH),)
	classpath:= $(CLASSPATH):$(classpath)
endif

JVFLAGS=-cp $(classpath)
export CLASSPATH:=$(classpath)

.SUFFIXES: .java .class
.PHONY: all run run-all clean tex zip

$(class_d)/OptimalTreeLabeling.class: $(source_d)/*.java
	@$(JAVAC) $(JFLAGS) $(source_d)/*.java

$(class_d):
	mkdir $(class_d)

all: $(class_d) $(class_d)/OptimalTreeLabeling.class

run:
	@$(JVM) $(JVFLAGS) OptimalTreeLabeling < $(FILE)

run-all:
	@$(source_d)/$(RUN) $(shell readlink -f $(FOLDER))

clean:
	@rm -rf $(class_d)/* *.zip *.pdf
	@cd $(latex_d) && $(MAKE) --no-print-directory clean

tex:
	@cd $(latex_d) && $(MAKE) --no-print-directory

zip:
	@rm -rf *.pdf *.zip
	@cp $(latex_d)/main.pdf ./
	@mv main.pdf $(PDF)
	@zip -r project.zip $(source_d) $(class_d) $(RM) $(PDF) Makefile
	@rm *.pdf
