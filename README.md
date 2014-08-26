Ceteris Paribus v0.1
===============

An attempt to model economic activity in a way that is not zero-sum.

This system is written in response to another programming exercise that claimed to model an economy. In that simulation, economic actors cast their homogeneous "wealth" units into a pot to be split randomly. Unsurprisingly, those who started out with more did better. Those with less did poorly. In that simulation, "equality" was only achieved by redistributive intervention after each transaction.

I felt that simulation modeled an economy very poorly for several reasons:

* Wealth is not homogeneous.
* Desires are not homogeneous. Different people value things differently.
* Trades are not win/lose transactions. Trades are necessarily win-win: each party gives something they value less to get something they value more. If this weren't the case they would not agree to trade.
* An economy is not zero-sum. Wealth is both created and destroyed.

This simulator will attempt to model an economy more accurately.

* Wealth is treated as heterogeneous
* Wealth is both created and consumed
* No presumptions are made about who is the winner or who is the loser in any given transaction.
* Rather than doling out a fixed number of assets and rolling the dice, this program will simulate the production, exchange, and consumption of wealth.


Structure
=========

The two key classes are Actor and Product.

Actors represent economic actors in an economy. Actors produce, consume, and maintain inventories of Products. Actors also have an ordinal list of which goods they desire, regardless of the rate at which they produce or consume them. Actors have some basic methods built into them already so you don't have to implement this behavior.

Products represent various goods in an economy. They have no methods as yet because goods are not the things that act. In this version they have only one attribute: Type.

There is also a Simulation class designed to help you craft a simulation of your own. To override the default setup, override Simulation's constructor.
