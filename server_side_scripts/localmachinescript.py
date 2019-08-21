#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Aug 10 18:29:09 2019

@author: abhijithneilabraham
"""
text1="Topic sentences are similar to mini thesis statements. Like a thesis statement, a topic sentence has a specific main point. Whereas the thesis is the main point of the essay, the topic sentence is the main point of the paragraph. Like the thesis statement, a topic sentence has a unifying function. But a thesis statement or topic sentence alone doesn’t guarantee unity. An essay is unified if all the paragraphs relate to the thesis, whereas a paragraph is unified if all the sentences relate to the topic sentence. Note: Not all paragraphs need topic sentences. In particular, opening and closing paragraphs, which serve different functions from body paragraphs, generally don’t have topic sentences."

def main(w_count):
    import fitz
    #import PyMuPDF
    from gensim.summarization.summarizer import summarize
    '''
    from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
    from pdfminer.converter import TextConverter
    from pdfminer.layout import LAParams
    from pdfminer.pdfpage import PDFPage
    from io import StringIO
    '''
    Summary=""
    pdf_document = "T3.pdf"
    doc = fitz.open(pdf_document)
    page_Count = doc.pageCount
    for i in range(0,int(page_Count)):
        page1 = doc.loadPage(i)
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
        W_Count=0
        if w_count==0:
            w_count=500
        else:
            W_Count=w_count
        out=summarize(text3,word_count=W_Count)
        Summary=Summary + " " +out
    lengther=0
    for i in Summary:
        lengther+=1
        if i=='.':
            break
            
    print(Summary[lengther:len(Summary)])
    #print(len(text))
    #print(text3[:2000])
    #print("count is ",count)
main(0)
