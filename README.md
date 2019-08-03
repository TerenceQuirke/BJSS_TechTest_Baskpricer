1.1 Pre-requisites 
==================
The candidate is expected to have access to a computer, email, a telephone (in order to speak to the organiser should the need arise) and software that can create Java or C# programs. 

It is also expected that the candidate will have a suitable professional environment in which to work. 

The candidate will be supplied with a telephone number to contact the organiser. 

1.2 Instructions 
================
The purpose of this assessment is to complete a simple programming assignment. 

The details of the assignment are elaborated in section 2. 

You are required to: 
--------------------
- Produce working, object-oriented and tested source code to solve the problem 
- Walk through your code with the assessor, answering questions on the code and programming/design choices as requested by the assessor These should be supplied to the organiser in electronic format, preferably as a complete project from your IDE of choice. 

If you have any issues with the programming assignment, please ensure that you inform the organiser immediately. 

You are expected to work on this task on your own, without help or advice from others. If you need clarification on any aspect of the assessment, please seek help from your organiser. 

You are given 2 days to complete this task. Even if the solution is not complete, the workings to that point should be submitted. 

 
2 Coding Assignment
===================
-------------------
2.1 Pricing a basket 
====================
Write a program and associated unit tests that can price a basket of goods taking into account some special offers. 

The goods that can be purchased, together with their normal prices are: 
- Soup – 65p per tin 
- Bread – 80p per loaf 
- Milk – £1.30 per bottle 
- Apples – £1.00 per bag 

Current special offers: 
-----------------------
- Apples have a 10% discount off their normal price this week 
- Buy 2 tins of soup and get a loaf of bread for half price 

The program should accept a list of items in the basket and output the subtotal, the special offer discounts and the final price. 

Input should be via a request

For example:
{
	"priceBasket":[ "item1", "item2", "item3" ]
}
 

Output should be a response, for example:

{
	"SubTotal": "£3.10",
	"SpecialOffers": "Apples 10% off: -10p",
	"Total": "£3.10"
}

If no special offers are applicable the code should output: 
{
	"SubTotal": "£3.10",
	"SpecialOffers": "(No offers available)"
	"Total": "£3.10"
}
The code and design should meet these requirements, but be sufficiently flexible to allow future changes to the product list and/or discounts applied. 
The code should be well structured, commented, have error handling and be tested. 
 
 