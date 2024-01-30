### Pana Adrian
### 323CC
#  README Tema POO - Catalog
## Grad de dificultate al temei 

-	mediu
##	Timp alocat rezolvarii
- 20-30 ore

# Detalii de implementare
#	1.1 Catalog
-	Pentru **Singleton**, are constructor privat si un atribut static **Catalog** returnat de metoda getInstance()
    
- Pe langa metodele din cerinta, am implementat gettere pentru a accesa mai rapid anumite elemente ale catalogului


#	1.2 Users
-	Am implementat gettere pentru nume pentru accesari mai usoare
#	1.5 Course
-	**CourseBuilder** contine aceleasi atribute ca si **Course** si metode pentru setarea individuala a fiecaruia dintre acestea. Metoda de *build()* a acestuia este abstracta si implementata in buildere pentru cele 2 tipuri de cursuri, acestea instantiind un obiect de tip **FullCourse** sau **PartialCourse** in *build()*
#	1.6 Observer
-	**Observer** -> **Parent**
-	**Subject** -> **Student**
-	Parintii se adauga in lista de observeri atunci cand sunt setati
-	Parintii contin un vector de notificati, *update()* adauga o notificare in vector
#	1.7 Strategy
-	**Course** contine o enumerare **TeacherStrat** pentru cele 3 strategii de notare
-	*getBestStudent()* instantiaza obiectul de tip *Strategy* in functie de strategia profesorului cursului si apeleaza functia corespunzatoare
#	1.8 Visitor
-	**Element** -> **Teacher**, **Assistant**
-	**ScoreVisitor** contine metode de adaugare a unei note in dictionarul pentru note de examen sau cel pentru partiale
-	Visitarea se face prin creearea unui Grade nou pentru fiecare tuple din dictionarul corespunzator, cautarea unui grade deja existent pentru student la cursul respectiv si modificarea acestuia sau adaugarea **Grade**-ului nou creat, apoi se goleste dictionarul
-	Pentru fiecare grade adaugat sau modificat, se notifica observerii studentului respectiv
#	1.9 Memento
-	*Clone()* creeaza un **Grade** nou cu atribute referinte noi la obiecte nou create
-	**Snapshot** contine o lista de **Grades** in care se stocheaza **grade**-urile din catalog la momentul apelarii *makeBackup()*
-	*Undo()* actualizeaza **grade**-urile din catalog la cele stocate in **Snapshot**
#	2.1 Student Page
-	Metoda *courses()* filtreaza cursurile la care participa studentul
-	La selectarea unui element din lista de cursuri se updateaza datele afisate despre cursul respectiv
#	2.2 Teacher/Assistant Page
-	Metoda *courses()* filtreaza cursurile la care participa profesorul/asistentul
-	Se iau notele nevalidate si se adauga in lista afisata
-	La apasarea butonului de validare, se accepta **ScoreVisitor**-ul de catre profesor/asistent si se goleste lista afisata
#	2.3 Parent Page
-	Contine un **Scroll Panel** cu o lista 
-	Metoda *updateNotificationList()* goleste lista si adauga toate notificarile stocate in obiectul **Parent** corespunzator. Aceasta metoda este apelata la fiecare *update()* al parintelui (la primirea unei noi notificari)
#	2.4 Login Page
-	Fereastra in care se selecteaza tipul de **User** si se introduc prenumele si numele acestuia, iar in cazul in care input-ul este valabil, se acceseaza pagina userului respectiv.


