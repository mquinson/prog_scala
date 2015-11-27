all: dipoles.pdf
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

dipoles.tex: dipoles.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

Exo1_template.tar.gz: $(wildcard src/*/*/*/*) build.sbt killparts
	rm -rf template
	for n in `find src -name '*.scala'` ; do \
	  m=`echo $$n|sed -e 's|src/|template/|'`; \
	  mkdir -p `dirname $$m` ; \
	  ./killparts  $$n $$m ; \
	done
	cd ..; tar cvfz Exercises1/Exo1_template.tar.gz --exclude-backups Exercises1/template Exercises1/build.sbt
