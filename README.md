Ceteris Paribus
===============

An attempt to model economic activity in a way that is not zero-sum.

This system is written in response to another programming exercise that claimed to model an economy. In this simulation, economic actors cast their homogeneous "wealth" units into a pot to be split randomly. Unsurprisingly, those who started out with more did better. Those with less did poorly. In this simulation, "equality" was only achieved by enforcing redistributive rules after each transaction.

I felt this modeled the nature of trades very poorly for several reasons:

* Wealth is not homogeneous.
* Desires are not homogeneous. Different people value things differently.
* Trades are not win/lose transactions. By definition, trades engender some benefit to each side, which is why the trade took place at all. This program will seek to simulate trades without judging one side to be a winner or loser.
* An economy is not zero-sum. Rather than doling out a fixed number of assets and rolling the dice, this program should simulate the production and destruction of wealth. The economic actors will produce, consume, and trade.