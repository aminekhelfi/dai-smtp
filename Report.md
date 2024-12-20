DAI lab: SMTP
=============

Description du projet
----------

Ce projet consiste à développer une application cliente TCP en Java qui utilise l'API Socket pour communiquer avec un serveur SMTP. L'application envoie automatiquement des farces par e-mail à une liste de victimes. Le but est de se familiariser avec le protocole SMTP et de concevoir un modèle orienté objet simple pour répondre aux exigences fonctionnelles.

Configuration du Serveur SMTP Fictif
-----------------------

Pour tester notre application sans envoyer de vrais e-mails, nous pouvons utiliser MailDev comme serveur SMTP fictif. Voici comment le configurer :

 - Docker doit être installé sur la machine
 - MailDev doit être démarré 
 
=> Utilisez la commande suivante pour démarrer MailDev :

    docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev

Cela fournira une interface Web sur localhost:1080 et un serveur SMTP sur localhost:1025.

Configuration et Exécution de l'Outil
-----------

**1. Cloner le dépôt : Clonez le dépôt GitHub contenant le code source de l'application.**

        git clone https://github.com/aminekhelfi/dai-smtp.git 
        cd .\dai-smtp\ 
**2. Configurer les fichiers : Modifier les fichiers de configuration pour inclure d'autres adresses e-mail et messages.**

`files/email.json` : Contient la liste des adresses e-mail.

`files/fishing_messages.json` : Contient les messages électroniques (objet et corps).

**3. Compiler et exécuter le programme avec Maven :**

       mvn clean package
       cd .\target
       java -jar <NOM.jar> <NombreGroupe>

Le pom.xml doit être configuré. Executer la commande `mvn clean package` là où se trouve le pom.xml.
En suite, un dossier `target` sera créer, et c'est là que doit être executé la commande `java -jar <NOM.jar> <NombreGroupe>`.


Description de l'Implémentation
-------

### Diagramme de Classes :

```mermaid
classDiagram
class PrankGenerator {
- List<String> victims
- List<String> messages
- SmtpClient smtpClient
- int nbGroupe
- int nbAddrMail
+ PrankGenerator(int nbgroupe, int nbAddrMail)
+ generateAndSendPranks() void
- checkEmailFormat(List<String> emailAddresses) boolean 
- createGroup(int nbAddrMail) Group
- getRandomMessage() List<String>
}

    class SmtpClient {
        - String smtpServer
        - int port
        + SmtpClient(String smtpServer, int port)
        + sendEmail(String sender, List<String> recipients, List<String> message) void
        - getActualDate() String
        - connexionServeurSMTP(BufferedReader in,BufferedWriter out, String sender, List<String> receiver) void
    }

    class Group {
        - String sender
        - List<String> recipients
        - List<String> message
        + Group(String sender, List<String> recipients, List<String> message)
        + getSender() String
        + getRecipients() List<String>
        + etMessage() List<String>
    }

    PrankGenerator --> SmtpClient
    PrankGenerator --> Group
```

**Classes Principales**

`PrankGenerator` : Cette classe est responsable de la génération et de l'envoi des farces. Elle lit les fichiers de configuration, valide les adresses e-mail, crée les groupes et envoie les e-mails.

`SmtpClient` : Cette classe gère la communication avec le serveur SMTP en utilisant des sockets. Elle envoie les e-mails en suivant le protocole SMTP.

`Group` : Cette classe représente un groupe de victimes avec un expéditeur, des destinataires et un message.

**Les autres classes du code :**

`json_reader` : Cette classe lit les adresses et les messages et les met dans des listes de String.

`Main` : Cette classe est la classe principale qui traite les arguments et créer le prank et appelle sa fonction pour l'envoyer.


### Exemple de Dialogue entre le Client et le Serveur SMTP :

Voici un exemple de dialogue entre le client et le serveur SMTP lors de l'envoi d'un e-mail (avec un seul destinataire) :

    serveur : 220 165026cd7919 ESMTP
    client  : ehlo localhost
    serveur : 250-165026cd7919 Nice to meet you, [172.17.0.1]
    serveur : 250-PIPELINING
    serveur : 250-8BITMIME
    serveur : 250 SMTPUTF8
    client  : mail from: <romain.hurni@heig-vd.ch>
    serveur : 250 Accepted
    client  : rcpt to: <amine.khelfi@heig-vd.ch>
    serveur : 250 Accepted
    client  : data
    serveur : 354 End data with <CR><LF>.<CR><LF>
    client  : From: <romain.hurni@heig-vd.ch>
    client  : To: <amine.khelfi@heig-vd.ch>
    client  : Date: 06.12.2024
    client  : Content-Type: text/plain; charset=UTF-8
    client  : Subject: Le soleil
    client  : Bonjour, le soleil est chaud, à bientôt !
    client  : .
    serveur : 250 Message queued as 2AGvUZYF
    client  : quit
    serveur : 221 Bye

>Note : 
> 
>Chaque envois du client est termineé par un `\n` et pour quitter le message, le client doit envoyé `\r\n . \r\n `.
>
>Pour que les caractères spéciaux soient pris en compte, il faut ajouter `Content-Type: text/plain; charset=UTF-8\r\n`.

**Résultat :**

![mail](mail_recu.jpg "Titre de l'image")
