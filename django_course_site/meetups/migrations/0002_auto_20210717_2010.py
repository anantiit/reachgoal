# Generated by Django 3.1.7 on 2021-07-17 14:40

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('meetups', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='meetup',
            name='description',
            field=models.TextField(null=True),
        ),
        migrations.AddField(
            model_name='meetup',
            name='slug',
            field=models.SlugField(default='null', unique=True),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='meetup',
            name='title',
            field=models.CharField(default='null', max_length=200),
            preserve_default=False,
        ),
    ]
