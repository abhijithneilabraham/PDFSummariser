#from __future__ import absolute_import
#from __future__ import division, print_function, unicode_literals
import pyrebase
import os
from PyPDF2 import PdfFileWriter, PdfFileReader
import sumy


from sumy.parsers.html import HtmlParser
from sumy.parsers.plaintext import PlaintextParser
from sumy.nlp.tokenizers import Tokenizer
from sumy.summarizers.lsa import LsaSummarizer as Summarizer
from sumy.nlp.stemmers import Stemmer
from sumy.utils import get_stop_words

#from gensim.summarization.summarizer import summarize
'''
from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
from pdfminer.converter import TextConverter
from pdfminer.layout import LAParams
from pdfminer.pdfpage import PDFPage
'''
import fitz
from io import StringIO,BytesIO
from fpdf import FPDF
from firebase_admin import credentials, firestore, storage

def func(file1,username,wc):
    Summary=""
    packet = BytesIO()
    packet.seek(0)

    filename="/"+file1
    #print(user)
    config ={
       "apiKey": "AIzaSyDvTZQo3KQIWvDmMwP16ItJ_DaJEylIGrc",
        "authDomain": "fir-android-c7a0d.firebaseapp.com",
        "databaseURL": "https://fir-android-c7a0d.firebaseio.com",
        "storageBucket": "fir-android-c7a0d.appspot.com"
        }
    firebase = pyrebase.initialize_app(config)
    stor = firebase.storage()
    #os.remove("T3.pdf")
    stor.child(filename).download("T3.pdf")
    pdf_document = "T3.pdf"
    doc = fitz.open(pdf_document)
    page_Count = doc.pageCount
    for v in range(0,int(page_Count)):
        page1 = doc.loadPage(v)
        pageText = page1.getText("text")
        # Get text from StringIO
        text = pageText
        text1=""
        text3=""
        count=0
        r = len(text)
        for i in range(1,r-1):
            if text[i]==" " and text[i+1]==" ":
                text.replace(text[i],"")
                count+=1

            if text[i]=='\t' or text[i]=='\n':
                text.replace(text[i]," ")
                count+=1
            r = len(text)
        t=0
        i=0
        j=0
        k=0
        flag1=0
        for i in range(t,len(text)):
            if text[i]=='.':
                for j in range(i+1,len(text)):
                    if text[j]=='.':
                        text1=text[i:j]
                        for k in text1:
                            flag1=0
                            if k in {':','!','-','(',')'}:
                                flag1=1
                                break
                        if flag1==1:
                            break
                        break
                if flag1==1:
                    continue
                else:
                    text3=text3+text[i:j]
            t=j
        r=0
        for i in range(0,r-1):
            if text3[i]=='.' and text3[i+1]!=' ':
                text3=text3.replace(text3[i+1],'')
            r=len(text3)
        w_count=int(wc)
        W_Count=0
        if w_count==0:
            w_count=50
        else:
            W_Count=w_count
        counters=0
        for p in text3:
            if p==" ":
                counters+=1
        if counters<20:
            continue
        #out=summarize(text3,ratio=(W_Count*.01))
        LANGUAGE = "english"
        SENTENCES_COUNT = W_Count
        parser = PlaintextParser.from_string(text3, Tokenizer(LANGUAGE))
        stemmer = Stemmer(LANGUAGE)
        out=""
        summarizer = Summarizer(stemmer)
        summarizer.stop_words = get_stop_words(LANGUAGE)
        for sentence in summarizer(parser.document, SENTENCES_COUNT):
            out+=str(sentence)
        if out=="":
            out = "Not enough words in this page to summarize"
        Summary=Summary + "\n\n Page No : "+str(v+1) + "\n\n"
        Summary=Summary + " " +out
    lengther=0
    for i in Summary:
        lengther+=1
        if i=='.':
            break
    out1 = Summary[lengther:len(Summary)]
    out = "Summary\n\n\n Page No: 1\n\n"+out1


    outfile='final.txt'
    with open(outfile,"w+") as filer:
        filer.write(out)
    filer.close()
    bucket = storage.bucket()

    blob = bucket.blob(str(username)+'/'+'final.txt')
    blob.upload_from_filename(outfile)
    #os.remove(outfile)


    return out
