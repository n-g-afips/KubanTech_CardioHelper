from django.shortcuts import get_object_or_404, render
from django.http import HttpResponse,HttpResponseRedirect
from django.urls import reverse
from django.template import loader

#def index(request):
#    return HttpResponse("Hello, world. You're at the polls index.")

from .models import Passport

def index(request):
    patients_list = Passport.objects.order_by('id')[:5]
    template = loader.get_template('cardiohelper/index.html')
    context = {
        'patients_list': patients_list,
    }
    return HttpResponse(template.render(context, request))

def detail(request, question_id):
    try:
        question = Passport.objects.get(pk=question_id)
    except Question.DoesNotExist:
        raise Http404("Question does not exist")
    return render(request, 'cardiohelper/detail.html', {'question': question})





# Create your views here.
