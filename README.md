# 📚 Biblio CLI Documentation

A command-line tool for managing books, users, and loans.

_By Adrien Dhommeaux, Matteo Belin and Paul Menard_

>## 📖 Guide
>**[🚀 Installation](#-Installation)**
>
>🛠️ **CLI Commands**
>- [General Help](#-Help-)
>- [Authentication](#-Authentification-)
>- [Books Management](#-Books-)
>- [Users Management](#-Users-)
>- [Loans Management](#-Loans-)
>
>📌 **Navigation Menu**
>- [Authentication Menu](#Auth-Menu)
>- [Home Menu](#Home)
>- [Admin Panel](#Admin)
>    
>**[❌ Uninstallation](#-Uninstall)**

## 🚀 Installation

Once the repository is cloned you need to :
- run `mvn clean install` to build the project
- open a terminal in the `target` folder
    - On Windows : 
        - run `biblio` for the menu and `biblio [commands]` for the CLI
    - On Linux : 
        - run `java --jar biblio.jar` for the menu and `java --jar biblio.jar [commands]` for the CLI

## 🛠️ CLI

### ⸻ Help ⸻
Displays general help or section-specific help.

```
biblio help
```
```
biblio auth help
```
```
biblio books help
```
```
biblio users help
```
```
biblio loans help
```

### ⸻ Authentification ⸻

#### Register
Creates a new user account.
```
biblio auth signin EMAIL PASSWORD [NAME]
    EMAIL        User's mail.
    PASSWORD     User's password.
    [NAME]       Specify user's name.
```


#### Login
Allows a user to log in with an email and password.
```
biblio auth login EMAIL PASSWORD [OPTIONS]
    EMAIL        User's mail.
    PASSWORD     User's password.repeatedly.
```

#### Logout
Logs out the current user.
```
biblio auth logout
```


### ⸻ Books ⸻

#### Add
Adds a new book to the library.
> [!Important]
> This command is only available for **ADMINS**

```
biblio books add NAME AUTHOR [GENRES]
    NAME         Book's name.
    AUTHOR       Author's name.
    [GENRES]     Specify one or multiple genres. See example.
```

> [!Note]
> If the specified author or genres do not exist, the system will prompt for confirmation before creating them.

> **💡 Examples**
> ```
> biblio books add "Le Petit Prince" "Antoine de Saint-Exupéry" "Fiction, Aventure"
> ``` 
> ```
> biblio books add "Le Petit Prince" "Antoine de Saint-Exupéry" Fiction Aventure
> ``` 


---

#### Modification
Modifies an existing book's details.
> [!Important]
> This command is only available for **ADMINS**
```
biblio books edit ID [OPTIONS]
    ID           Book's id. see list command to find any book's id.
    [OPTIONS]:
    --author, -a Author's name.
    --genre, -g  Specify one or multiple genres using ',' as separator. See example.
    --title, -t  Book's title.
```
> [!Note]
> If the specified author or genres do not exist, the system will prompt for confirmation before creating them.

> **💡 Example**
> ```
> biblio books edit 3 --author "Antoine de Saint-Exupéry" --genre "Fiction, Aventure" --title "Le Petit Prince"
> ``` 
---

#### Delete
Removes an existing book from the library.
> [!Important]
> This command is only available for **ADMINS**

```
biblio books delete ID
    ID           Book's id.
```

---

#### List
Displays a list of all the books in the library
```
biblio books list
```

---
#### Search
Search a book in the library by it's Title, Author or Genres.
> [!Note]
> The search is case insensitive and matches any term containing the query
```
biblio books search SEARCH_TEXT
    SEARCH_TEXT the search query used to find books in the library. 
```

---
#### Unborrowed
Displays a list of books that are currently available for borrowing.

```
biblio books unborrowed 
```
---

#### Export
Exports books data to a specified JSON file.
> [!Important]
> This command is only available for **ADMINS**

> [!Note] 
> If the path (folders) doesn't exist it will be created 
```
biblio books export PATH 
```


---

#### Import
Imports books data from a specified JSON file.
> [!Important]
> This command is only available for **ADMINS**

```
biblio books import PATH
    PATH         Import's file path.
```


---



### ⸻ Users ⸻

#### Add
Registers a new user in the system.
> [!Important]
> This command is only available for **ADMINS**
> A version of this command is available for common users. See [register](#Register) command for more information.
```
biblio users add [FLAGS] EMAIL PASSWORD
    [FLAGS]
    --admin, -a  Sets the user as an admin. See example.
```
>  **💡 Example**
> ```
> biblio users add --admin root@biblio.org ROOTPASSWORD
> ``` 
---


#### Modification
Updates user information.
> [!Important]
> This command is available for **logged users**
> see [Auth](#Authentification) section for more informations
>
> The email argument is only available for **ADMINS**, in other case, the current logged user's email will be used for the command.
> The flags argument is only available for **ADMINS**
```
biblio users edit [EMAIL] [OPTIONS]
    [EMAIL]      Identify the user to be updated by their email.
    [OPTIONS]:
    --email, -e  Specify the new email.
    --name, -n   Specify the new name.
    --admin, -a  Specify if the user is admin. [y/n]
```
>  **💡 Example**
> ```
> biblio users edit root@biblio.org -a n -n "ROOT" -e "newroot@biblio.org"
> ``` 
---

#### Delete
Removes a user from the system.
> [!Important]
> This command is available for **logged users**.
> See the [Auth section](#Authentification) for more informations.
>
> The email argument is only available for **ADMINS**, in other case, the current logged user's email will be used for the command.
```
biblio users delete [EMAIL]
    [EMAIL]      Identify the user to be deleted by their email.
```

### ⸻ Loans ⸻

#### Loan
Allows a user to borrow a book.
> [!Important]
> This command is available for **logged users**.
> See the [Auth section](#Authentification) for more informations.
>
> The email argument is only available for **ADMINS**, in other case, the current logged user's email will be used for the command.
```
biblio loans add BOOK_ID [EMAIL]
    [EMAIL]      Identify which user borrows the book by their email.
```
---

#### Return
Allows a user to return a borrowed book.
> [!Important]
> This command is available for **logged users**.
> See the [Auth section](#Authentification) for more informations.
>
> The email argument is only available for **ADMINS**, in other case, the current logged user's email will be used for the command.
```
biblio loans return BOOK_ID [EMAIL]
    [EMAIL]      Identify which user returns the book by their email.
```
---

#### History
Displays the borrowing history of a user.
> [!Important]
> This command is available for **logged users**.
> See the [Auth section](#Authentification) for more informations.
>
> The email argument is only available for **ADMINS**, in other case, the current logged user's email will be used for the command.
```
biblio loans history [EMAIL]
    [EMAIL]      Identify which user's borrowing history to display by their email.
```
---

#### Currents
Lists all books currently borrowed by a user.
> [!Important]
> This command is available for **logged users**.
> See the [Auth section](#Authentification) for more informations.
>
> The email argument is only available for **ADMINS**, in other case, the current logged user's email will be used for the command.
```
biblio loans currents [EMAIL]
    [EMAIL]      Identify which user's currents borrowing to display by their email.
```
# 🚧 En cours d'implémentation 
## 📌 Menu

### Auth Menu

```
--------------------
Welcome to Biblio ! 
--------------------
    1. search a book
    2. login
    3. sign in
```

### Home

```
---------------------------
Welcome back [USER_NAME] ! 
Home
---------------------------
What do you want to do :
    0. admin (ADMIN ONLY)
    1. search a book
    2. loan a book
    3. return a book
    4. see current loans
    5. see loan history
    6. manage account
    7. exit
```

### Admin 

```
---------------------------
Welcome back [USER_NAME] !
Home > Admin
---------------------------
What do you want to do :
    0. back to Home
    1. manage books
    2. manage users
    3. manage loans
    4. manage authors
    5. manage genres
```
###### Manage Books
```
----------------------------
Welcome back [USER_NAME] !
Home > Admin > Manage Books
----------------------------
What do you want to do :
    0. back to Admin
    1. add a book
    2. edit a book
    3. remove a book
```

###### Manage Users

```
----------------------------
Welcome back [USER_NAME] !
Home > Admin > Manage Users
----------------------------
What do you want to do :
    0. back to Admin
    1. add a user
    2. edit a user
    3. remove a user
```

###### Manage Loans

```
----------------------------
Welcome back [USER_NAME] !
Home > Admin > Manage Loans
----------------------------
What do you want to do :
    0. back to Admin
    1. add a loan
    2. return a loan
    3. see loan history
    4. see current loans
```

###### Manage Authors

```
------------------------------
Welcome back [USER_NAME] !
Home > Admin > Manage Authors
------------------------------
What do you want to do :
    0. back to Admin
    1. add an author
    2. edit an author
    3. remove an author
```

###### Manage Genres

```
-----------------------------
Welcome back [USER_NAME] !
Home > Admin > Manage Genres
-----------------------------
What do you want to do :
    0. back to Admin
    1. add a genre
    2. edit a genre
    3. remove a genre
```

## ❌ Uninstall
