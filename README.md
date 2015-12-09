# Programming Large Systems with Scala

This repository contains the teaching material that I use for my
lectures on System Programming at [ENS Rennes](http://ens-rennes.fr/). 
This is a undergrad lecture, introductionnary on scala (but not on
programming).

If you are a student of ENS Rennes, you will not find any solution to
the exercises. You should not read the material before the lectures to
not spoil yourself, but it does not hurt if you do. After the lecture,
your help is welcome to improve the material. Pull requests welcome ;)

## Syllabus

### *Lecture 1: [Introduction](https://github.com/mquinson/prog_scala/raw/master/Lecture1/scala_lect1.pdf).*

  We first try to motivate the study of systems programming, and
  discuss the philosophy of Computer Science to show that Programming
  large Systems is absolutely central in Computer Science. 
  
  After this (hopefully) motivating introduction, we present the
  basics of the Scala syntax.

### *Practical 1: [First steps with Scala on the PLM](https://plm.telecomnancy.univ-lorraine.fr/#/ui/lessons/recursion.hanoi/).*

  We use the lesson on Hanoï Towers of [the PLM](https://github.com/BuggleInc/PLM) 
  to experiment with the Scala syntax. Don't forget to switch to
  Scala with the upper right menu!

### *Lecture 2: [Dealing with Complexity](https://github.com/mquinson/prog_scala/raw/master/Lecture2/scala_lect2.pdf).*

  Large systems are inherently complex and dynamic, so we want to
  design our systems in a way that mitigates these issues.
  
  We lengthly discuss the Object Oriented Programming approach (OOP),
  that is the classical reductionist solution to the system
  complexity. There is no absolute rule about how to build a Good
  Design (TM), but we present several rules of thumb to avoid horrible
  designs, at least.

  As a conclusion (and as an appertizer for next week's lecture) we
  then shortly contrast this OOP approach with the Functional
  Programming (FP) way of composing complex systems.

### *Project: [Ants vs. SomeBees](https://github.com/mquinson/prog_scala/blob/master/Coursework_Ants/Ants.org)* ([pdf](https://github.com/mquinson/prog_scala/raw/master/Coursework_Ants/Ants.pdf))

  In this project, you will implement a [Tower Defense](https://secure.wikimedia.org/wikipedia/en/wiki/Tower_defense) 
  game inspired from the PopCap Games'
  [Plants vs Zombies](http://www.popcap.com/games/pvz/web) ®.
  During this assignment, you will explore many of the OOP concepts,
  as well as some basic concepts of FP.
  
  Please **do not fork the current project** to work on your
  assignment, but [download the source code](https://github.com/mquinson/prog_scala/raw/master/Coursework_Ants/Coursework_Ants.tar.gz)
  and create another repository elsewhere. Please do not post any
  solution to this assignment online. 

### *Lecture 3: [Functional Programming](https://github.com/mquinson/prog_scala/raw/master/Lecture3/scala_lect3.pdf).*
  
  This week we explore another way of sorting your bits, centered on
  the behaviors. We speak of higher order functions and closures, but
  also of variance, covariance, invariance and friends.

### *Practical 2: [Inheritance, Overriding and Dynamic Binding](https://github.com/mquinson/prog_scala/raw/master/Practical2/scala_exo2.pdf)*

  This exercise sheet will let you work on OOP code organization. You
  will have to design two class hierarchies. A 
  [source template](https://github.com/mquinson/prog_scala/raw/master/Practical2/scala_exo2.tar.gz)
  is provided to save your time.
  
### *Lecture 4: [Sorting your bits](https://github.com/mquinson/prog_scala/raw/master/Lecture4/scala_lect4.pdf).*
  
  This week we speak of code clarity and understandable design. This
  will lead us through the notions of Design Patterns, code smell,
  Functionnal Programming Principles and Programming Style.

### *Practical 3: [Functional Programming and Combinatorial Search](https://github.com/mquinson/prog_scala/raw/master/Practical3/scala_exo3.pdf)*

  This exercise sheet will let you work on FP code organization, using
  two classical combinatorial search problems as an excuse. A
  [source template](https://github.com/mquinson/prog_scala/raw/master/Practical3/scala_exo3.tar.gz)
  is provided to save your time.

## Compiling the material

Everything is written with org-mode, of emacs. If you are really new
to this, check the [little introduction](http://people.irisa.fr/Martin.Quinson/Research/Students/Methodo/)
that I wrote in another context.

If you want to run everything with a simple ```make```, you need to
also install [latex-make](https://www.ctan.org/pkg/latex-make). That's
very easy if you use a Linux system, but I was told that it's also
possible with Mac OSX. 

## License

This material is distributed under the CC-BY-SA licence: as with
wikipedia material, you are free to contribute or distribute this
content, but you cannot restrict the rights of your own copy. Your
readers must also be allowed to modify and contribute to your version.