all: scala_lect2.pdf
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

scala_lect2.tex: scala_lect2.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-beamer-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

4up.pdf: scala_lect2.pdf
	beamer2handout scala_lect2.pdf 4up 4up.pdf
8up.pdf: scala_lect2.pdf
	beamer2handout scala_lect2.pdf 8up 8up.pdf
25up.pdf: scala_lect2.pdf
	beamer2handout scala_lect2.pdf 25up 25up.pdf
