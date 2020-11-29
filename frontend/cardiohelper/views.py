from django.shortcuts import get_object_or_404, render
from django.http import HttpResponse,HttpResponseRedirect
from django.urls import reverse
from django.template import loader
#from cardiohelper.backend import *
import pickle
import pandas as pd




#def index(request):
#    return HttpResponse("Hello, world. You're at the polls index.")

from .models import Passport

def index(request):
    patients_list = Passport.objects.order_by('id')[:100]
    template = loader.get_template('cardiohelper/index.html')
    context = {
        'patients_list': patients_list,
    }
    return HttpResponse(template.render(context, request))

def detail(request,id):
    patients_list = Passport.objects.order_by('id')[:100]
    file = open('/home/kolyan/hackathon/model.pickle', 'rb')
    model = pickle.load(file)
    risk = model.predict_proba((pd.read_json('/home/kolyan/hackathon/data.json')).loc[id].values)[1]
    #return HttpResponse(risk)
    return render(request, 'cardiohelper/detail.html', {'risk': risk})


def form(request):
    context= {}
    template = loader.get_template('cardiohelper/form.html')
    return HttpResponse(template.render(context, request))


# Create your views here.
