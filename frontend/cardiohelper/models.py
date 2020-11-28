from django.db import models

class Passport(models.Model):
        id = models.CharField(max_length=13,primary_key=True)
        area = models.IntegerField()
        birthdate=models.CharField(max_length=13)
        age = models.IntegerField()



