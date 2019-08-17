
from flask import Flask, render_template, request
import pyrebase
import os
from gensim.summarization.summarizer import summarize
from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
from pdfminer.converter import TextConverter
from pdfminer.layout import LAParams
from pdfminer.pdfpage import PDFPage
from io import StringIO
import firebase_admin
from firebase_admin import credentials, firestore
#from google.cloud import storage
from ReturnFunc import func
cred=credentials.Certificate('/home/abhijithneilabraham/mysite/anacredentials.json')
firebase_admin.initialize_app(cred, {'storageBucket': 'fir-android-c7a0d.appspot.com'})
db = firestore.client()
#bucket = storage.bucket()
app = Flask(__name__)

@app.route("/<user>/<count>/")
def my_form(user,count):
    filename1=str(user)+"/"+"test.pdf"
    print(filename1)
    print(user)
    print(str(user))
    if filename1!=str(user)+"/"+"favicon.ico.pdf" and str(count)!="favicon.ico":
        func(filename1,str(user),count)
    return "successful"
if __name__=="__main__":
    app.run()
