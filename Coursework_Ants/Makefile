# This little Makefile should make it easy to compile your project.
# At least, I hope.


# Give the name of your main class below
MYAPP=DemoApp

# You should not need to change anything below this line #
# Simply put your scala files in the src/ directory.     #
##########################################################


# by default, we try to run the game after recompiling
all: run

##
## How to rebuild the code and execute it
##

# just recompile everything on need, it's much easier
$(MYAPP).class: $(wildcard src/*.scala) 
	mkdir -p bin
	cd bin; scalac -cp ../scala-swing.jar:. ../src/*.scala

# Rule to execute your application. Don't change it.
run: $(MYAPP).class
	scala -cp scala-swing.jar:bin:gfx $(MYAPP)

## 
## How to clean things
##
clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex src/*.class *.class bin

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


.PHONY: run class_files
