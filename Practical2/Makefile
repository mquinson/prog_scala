all: scala_exo2.pdf scala_exo2.tar.gz
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

scala_exo2.tex: scala_exo2.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

scala_exo2.tar.gz: $(wildcard src/*/*/*/*) build.sbt killparts Makefile
	rm -rf scala_exo2
	mkdir scala_exo2
	for n in `find src -name '*.scala'` ; do \
	  m=`echo $$n|sed -e 's|^|scala_exo2/|'`; \
	  mkdir -p `dirname $$m` ; \
	  ./killparts  $$n $$m ; \
	done
	cp build.sbt scala_exo2
	tar cvfz scala_exo2.tar.gz --exclude-backups scala_exo2
