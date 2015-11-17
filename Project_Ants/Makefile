all: Ants.pdf
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

Ants.tex: Ants.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

4up.pdf: Ants.pdf
	beamer2handout Ants.pdf 4up 4up.pdf
8up.pdf: Ants.pdf
	beamer2handout Ants.pdf 8up 8up.pdf
25up.pdf: Ants.pdf
	beamer2handout Ants.pdf 25up 25up.pdf
