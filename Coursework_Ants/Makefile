all: Ants.pdf Coursework_Ants.tar.gz 
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex src/*.class *.class bin

bin/AntsApp.class: src/AntsApp.scala
	mkdir -p bin
	cd bin; scalac -cp ../scala-swing.jar:.. ../src/AntsApp.scala

run: bin/AntsApp.class
	scala -cp scala-swing.jar:bin:gfx AntsApp

Ants.tex: Ants.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

Coursework_Ants.tar.gz: $(wildcard src/*) $(wildcard gfx/*) build.sbt 
	cd ..; tar cvfz Coursework_Ants/Coursework_Ants.tar.gz --exclude-backups Coursework_Ants/src Coursework_Ants/gfx Coursework_Ants/build.sbt 

.PHONY: run