all: OOP-dipoles.pdf OOP-dipoles.tar.gz
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

OOP-dipoles.tex: OOP-dipoles.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

OOP-dipoles.tar.gz: $(wildcard src/*/*/*/*) build.sbt killparts Makefile
	rm -rf OOP-dipoles
	mkdir OOP-dipoles
	for n in `find src -name '*.scala'` ; do \
	  m=`echo $$n|sed -e 's|^|OOP-dipoles/|'`; \
	  mkdir -p `dirname $$m` ; \
	  ./killparts  $$n $$m ; \
	done
	cp build.sbt OOP-dipoles
	tar cvfz OOP-dipoles.tar.gz --exclude-backups OOP-dipoles
	rm OOP-dipoles