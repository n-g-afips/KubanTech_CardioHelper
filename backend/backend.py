import pickle

file = open('model.pickle', 'rb')
model = pickle.load(file)
model.predict_proba('')


#pickle.load(open(filename, 'rb'))
#model = pickle.load(open(model.pickle, 'rb'))
#model.predict_proba()
