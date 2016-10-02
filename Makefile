PROJECT=sentiment-analysis
DOCUMENT=main
SOURCE_FOLDER=src/doc/tex
TARGET_FOLDER=target/documentation

create_target:
	mkdir -p $(TARGET_FOLDER)

clean: create_target
	find $(TARGET_FOLDER) -name '*.*' ! -name '*.gitignore' -delete

clean_source:
	find $(SOURCE_FOLDER) -name '*.*' ! -name '*.tex' -delete

package: create_target create_pdf
	mv $(SOURCE_FOLDER)/$(DOCUMENT).pdf $(TARGET_FOLDER)/$(PROJECT).pdf

create_pdf:
	cd $(SOURCE_FOLDER) && \
	pdflatex -shell-escape $(DOCUMENT) && \
	bibtex $(DOCUMENT) && \
	pdflatex -shell-escape $(DOCUMENT) && \
	pdflatex -shell-escape $(DOCUMENT)

all: clean package clean_source