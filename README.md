Ceteris Paribus
===============

An attempt to model economic activity in a way that is not zero-sum.

This system is written in response to another programming exercise that claimed to model an economy. In that simulation, economic actors cast their homogeneous "wealth" units into a pot to be split randomly. Unsurprisingly, those who started out with more did better. Those with less did poorly. In this simulation, "equality" was only achieved by enforcing redistributive rules after each transaction.

I felt that simulation modeled an economy very poorly for several reasons:

* Wealth is not homogeneous.
* Desires are not homogeneous. Different people value things differently.
* Trades are not win/lose transactions.
* An economy is not zero-sum.

This simulator will attempt to model an economy more accurately.

* Wealth is treated as heterogeneous
* Wealth is both created and consumed
* No presumptions are made about who is the winner or who is the loser in any given transaction.
* Rather than doling out a fixed number of assets and rolling the dice, this program will simulate the production and destruction of wealth.