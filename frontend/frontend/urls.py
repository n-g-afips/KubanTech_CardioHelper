from django.contrib import admin
from django.urls import include, path

urlpatterns = [
    path('cardiohelper/', include('cardiohelper.urls')),
    path('admin/', admin.site.urls),
]
