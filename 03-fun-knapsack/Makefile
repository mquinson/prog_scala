all: fun-knapsack.pdf fun-knapsack.tar.gz
PDFLATEX_OPTIONS=-shell-escape

include LaTeX.mk

clean::
	rm -rf cpy.pdf *~ *vrb _region_.tex fun-knapsack bin/*

fun-knapsack.tex: fun-knapsack.org
	emacs -batch --eval "(setq enable-local-eval t)" --eval "(setq enable-local-variables t)" $^  --funcall org-latex-export-to-latex
	mv $@ $@.bak
	echo '\\def\\raggedright{}' > $@
	echo "\PassOptionsToPackage{svgnames}{xcolor}" >> $@
	cat $@.bak >> $@
	rm $@.bak

fun-knapsack.tar.gz: $(wildcard src/*/*/*/*) build.sbt killparts Makefile
	rm -rf fun-knapsack
	mkdir fun-knapsack
	for n in `find src -name '*.scala'` ; do \
	  m=`echo $$n|sed -e 's|^|fun-knapsack/|'`; \
	  mkdir -p `dirname $$m` ; \
	  ./killparts  $$n $$m ; \
	done
	cp build.sbt fun-knapsack
	tar cvfz fun-knapsack.tar.gz --exclude-backups fun-knapsack
	rm -rf fun-knapsack
