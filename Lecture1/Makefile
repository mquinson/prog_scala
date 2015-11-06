all: scala_lect1.pdf

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

scala_lect1.tex: scala_lect1.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-beamer-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	cat $@.bak >> $@
	rm $@.bak

4up.pdf: scala_lect1.pdf
	beamer2handout scala_lect1.pdf 4up 4up.pdf
25up.pdf: scala_lect1.pdf
	beamer2handout scala_lect1.pdf 25up 25up.pdf
	