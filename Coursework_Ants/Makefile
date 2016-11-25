all: Ants.pdf Coursework_Ants.tar.gz
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex src/*/*.class

Ants.tex: Ants.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

Coursework_Ants.tar.gz: $(wildcard src/*/*) build.sbt FirstTest.scala
	cd ..; tar cvfz Coursework_Ants/Coursework_Ants.tar.gz --exclude-backups Coursework_Ants/src Coursework_Ants/build.sbt Coursework_Ants/FirstTest.scala

4up.pdf: Ants.pdf
	beamer2handout Ants.pdf 4up 4up.pdf
8up.pdf: Ants.pdf
	beamer2handout Ants.pdf 8up 8up.pdf
25up.pdf: Ants.pdf
	beamer2handout Ants.pdf 25up 25up.pdf
