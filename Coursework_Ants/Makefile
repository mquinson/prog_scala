all: class_files Ants.pdf Coursework_Ants.tar.gz 
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex src/*.class *.class bin

# Just list the dependencies here
bin/engine/Engine.class: src/Engine.scala
bin/DemoApp.class: src/DemoApp.scala bin/engine/Engine.class

# List one class file built out of each of your scala files
class_files: bin/engine/Engine.class bin/DemoApp.class 

# Default rule to build your scala files
bin/engine/%.class: src/%.scala
	mkdir -p $(dir $@)
	cd bin; scalac -cp ../scala-swing.jar:. ../$<
bin/%.class: src/%.scala
	mkdir -p $(dir $@)
	cd bin; scalac -cp ../scala-swing.jar:. ../$<

run: class_files
	scala -cp scala-swing.jar:bin:gfx DemoApp

Ants.tex: Ants.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

Coursework_Ants.tar.gz: $(wildcard src/*) $(wildcard gfx/*) build.sbt Makefile Ants.pdf scala-swing.jar
	cd ..; tar cvfz Coursework_Ants/Coursework_Ants.tar.gz --exclude-backups $(foreach f, $^, Coursework_Ants/$(f))

.PHONY: run class_files