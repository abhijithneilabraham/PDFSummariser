import pyrebase
import os
from gensim.summarization.summarizer import summarize
from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
from pdfminer.converter import TextConverter
from pdfminer.layout import LAParams
from pdfminer.pdfpage import PDFPage
from io import StringIO
from fpdf import FPDF
import firebase_admin
from firebase_admin import credentials, firestore, storage
def func(file1,username):
    filename=file1
    #print(user)
    config ={
       "apiKey": "AIzaSyDvTZQo3KQIWvDmMwP16ItJ_DaJEylIGrc",
        "authDomain": "fir-android-c7a0d.firebaseapp.com",
        "databaseURL": "https://fir-android-c7a0d.firebaseio.com",
        "storageBucket": "fir-android-c7a0d.appspot.com"
        }
    firebase = pyrebase.initialize_app(config)
    stor = firebase.storage()
    stor.child(filename).download("T3.pdf")
    pdfname="T3.pdf"
    rsrcmgr = PDFResourceManager()
    sio = StringIO()
    codec = 'utf-8'
    laparams = LAParams()
    device = TextConverter(rsrcmgr, sio, codec=codec, laparams=laparams)
    interpreter = PDFPageInterpreter(rsrcmgr, device)

    # Extract text
    fp = open(pdfname, 'rb')
    for page in PDFPage.get_pages(fp):
        interpreter.process_page(page)
    fp.close()

    # Get text from StringIO
    text = sio.getvalue()

    # Cleanup
    device.close()
    sio.close()

    out=summarize(text,word_count=200)
    pdf = FPDF()
    pdf.add_page()
    pdf.set_font("Arial", size=12)
    count1=0
    count2=0
    flag=0
    for i in out:
        count1+=1
        count2+=1
        if count2>80 and i==" ":
            count2=0
            pdf.cell(200, 10, txt=out[flag:count1], ln=1, align="L")
            flag=count1
            continue
        if count1==len(out):
            pdf.cell(200, 10, txt=out[flag:count1], ln=1, align="L")

    bucket = storage.bucket()
    outfile='bomm.pdf'
    pdf.output(outfile)
    blob = bucket.blob(str(username)+'/'+'final.pdf')
    blob.upload_from_filename(outfile)
    os.remove(outfile)
    os.remove("T3.pdf")
    return 0
