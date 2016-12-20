all: OOP-design.pdf OOP-design.tar.gz
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex

OOP-design.tex: OOP-design.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

OOP-design.tar.gz: $(wildcard src/*/*/*/*) build.sbt killparts Makefile
	rm -rf 01-OOP-design
	mkdir 01-OOP-design
	for n in `find src -name '*.scala'` ; do \
	  m=`echo $$n|sed -e 's|^|01-OOP-design/|'`; \
	  mkdir -p `dirname $$m` ; \
	  ./killparts  $$n $$m ; \
	done
	cp build.sbt 01-OOP-design
	tar cvfz 01-OOP-design.tar.gz --exclude-backups 01-OOP-design
