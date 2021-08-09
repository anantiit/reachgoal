from django.db import models

# Create your models here.
class MeetUp(models.Model):
        title=models.CharField(max_length=200)
        slug=models.SlugField(unique=True)
        description=models.TextField(null=True)
        image = models.ImageField(upload_to='images',null=True)


