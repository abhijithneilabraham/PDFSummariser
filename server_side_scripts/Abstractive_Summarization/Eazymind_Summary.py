from eazymind.nlp.eazysum import Summarizer
key = "325ebb2261b4ddc22aa8c8f5c480a73d" #APIKey

sentence = "Facebook CEO Mark Zuckerberg, left, makes the keynote speech at F8, the Facebook's developer conference, Tuesday, April 30, 2019, in San Jose, Calif Facebook says that, unlike its past, its future is privacy A trader works ahead of the closing bell on the floor of the New York Stock Exchange (NYSE) on April 12, 2019 in New York City. (Photo by Johannes EISELE / AFP)        (Photo credit should read JOHANNES EISELE/AFP/Getty Images)Resilience is still the word for stocks"


summarizer = Summarizer(key)
print(summarizer.run(sentence))
