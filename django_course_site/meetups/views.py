from django.shortcuts import render
from .models import MeetUp
#from django.http import HttpResponse

# Create your views here.
def index(request):
	#return HttpResponse("Hello there") try #1
	#allmeetups=[{"title":"My first meetup"},{"title":"My second meetup"}] try #2
	allmeetups = MeetUp.objects.all();
	return render(request, 'meetups/index.html', {"show_meets":True, "meetupslist":allmeetups})
