from __future__ import absolute_import
from __future__ import division, print_function, unicode_literals

from sumy.parsers.html import HtmlParser
from sumy.parsers.plaintext import PlaintextParser
from sumy.nlp.tokenizers import Tokenizer
from sumy.summarizers.lsa import LsaSummarizer as Summarizer
from sumy.nlp.stemmers import Stemmer
from sumy.utils import get_stop_words

import fitz
#import PyMuPDF
pdf_document = "joc.pdf"
text=""
doc = fitz.open(pdf_document)
page_Count = doc.pageCount
for f in range(0,int(page_Count)):
    page1 = doc.loadPage(f)
    pageText = page1.getText("text")
    text = text + "\n\n" +pageText


LANGUAGE = "english"
SENTENCES_COUNT = 20


if __name__ == "__main__":
    parser = PlaintextParser.from_string(text, Tokenizer(LANGUAGE))
    stemmer = Stemmer(LANGUAGE)

    summarizer = Summarizer(stemmer)

    summarizer.stop_words = get_stop_words(LANGUAGE)
    for sentence in summarizer(parser.document, SENTENCES_COUNT):
        print(sentence)



