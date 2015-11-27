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

scala_exo2.tar.gz: $(wildcard src/*/*/*/*) build.sbt killparts
	rm -rf template
	for n in `find src -name '*.scala'` ; do \
	  m=`echo $$n|sed -e 's|src/|template/|'`; \
	  mkdir -p `dirname $$m` ; \
	  ./killparts  $$n $$m ; \
	done
	cd ..; tar cvfz Practical2/scala_exo2.tar.gz --exclude-backups Practical2/template Practical2/build.sbt
