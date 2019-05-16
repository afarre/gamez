El llenguatge usat per parlar amb el bot és l'anglès, ja que per usar les entities de BotEngine és necessari.

Per tal d'executar el programa, cal:
- Tenir IntelliJIdea per poder executar el codi Java.
- Tenir una versió de Java 11 o superior (la que s'ha usat per la programació).

En el cas que hi hagi un problema a l'executar el projecte un cop descarregat des de GitHub (ha passat més del que es voldria), es recomana:
- Comprovar els mòduls de l'estructura del projecte.
- Comprovar la JDK de l'estructura del projecte (11+).
- Comporvar l'assignació de les llibreries del projecte.
- ...

En el cas que es volgués que es tornés a no reconèixer l'usuari (un desconegut), caldria esborrar el nom d''usuari del fitxer "user.json" i deixar-ho a "".

El flow del diàleg amb el bot es pot resumir en:
1. Si et coneix el bot, anar al pas 4, sinó al pas 3.
2. Demana el nom de l'usuari i la seva edat.
3. Pregunta si vol fer un formulari per buscar un joc amb certs paràmetres. Si no vol, anar al pas 7, sinó anar al pas 4.
4. Pregunta el formulari i mostra els valors introduïts.
5. Pregunta si els valors són correctes. Si no ho són, es torna al pas 4, sinó es va al pas 6.
6. Es mostren els resultats i es pregunta si es vol tornar a fer el formulari. Si vol, anar al pas 4, sinó anar al pas 7.
7. S'espera a una interacció de l'usuari (ajuda, comiat, formulari o cerca respecte anteriors cerques). Si vol ajuda, anar al pas 8, si s'acomiada anar al pas 12, si vol formulari, anar al pas 9, si vol una cerca, anar al pas 10.
8. Mostra les opcions disponibles. Tornar al pas 7.
9. Confirma si es vol fer el formulari. Si es vol, anar al pas 4, sinó anar al pas 7.
10. Confirma si es vol cercar jocs similars als ja buscats. Si es vol, anar al pas 11, sinó anar al pas 7.
11. Es mostren els resultats obtinguts. Tornar al pas 7.
12. Acaba el diàleg i l'execució.
