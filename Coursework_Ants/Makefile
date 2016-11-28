# This little Makefile should make it easy to compile your project.
# At least, I hope.


# Give the name of your main class below
MYAPP=DemoApp

# by default, we try to run the game
all: run

##
## How to rebuild the code and execute it
##

# If you add a package to your game, add bin/${yourpackage} to the following list
VPATH = bin:bin/engine:src

# List the dependencies of each file here.
# for each file, give ${className}.class: ${filename}.scala + the classes on which you depend
Engine.class: Engine.scala
DemoApp.class: DemoApp.scala Engine.class


# you should not need to change anything below this line

# Default rule to build your scala files. Don't change it.
%.class: %.scala
	mkdir -p bin
	cd bin; scalac -cp ../scala-swing.jar:. ../$<

# Rule to execute your application. Don't change it.
run: $(MYAPP).class
	scala -cp scala-swing.jar:bin:gfx $(MYAPP)

##
## How to rebuild the project sheet from the .org source
## 

ifeq ($(wildcard /usr/include/LaTeX.mk),)
Ants.pdf: Ants.org
	echo "You need to install emacs, org-mode and latex-make to recompile Ants.pdf" 
else
PDFLATEX_OPTIONS=-shell-escape
include LaTeX.mk
Ants.tex: Ants.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak
endif

##
## How to rebuild the template tar.gz
##

Coursework_Ants.tar.gz: $(wildcard src/*) $(wildcard gfx/*) build.sbt Makefile Ants.pdf scala-swing.jar
	cd ..; tar cvfz Coursework_Ants/Coursework_Ants.tar.gz --exclude-backups $(foreach f, $^, Coursework_Ants/$(f))

## 
## How to clean things
##
clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex src/*.class *.class bin

.PHONY: run class_files
