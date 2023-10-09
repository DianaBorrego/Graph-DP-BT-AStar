# Robots

There are $n=4$ types of materials $(A, B, C, D)$. For each one, there is a robot that can generate it, but manufacturing that robot has a cost in materials. When a robot is manufactured, it produces one unit of its material per minute indefinitely. You start with 1 robot of A and 0 units of all materials.
At the beginning of each minute (state) you can decide whether to build a robot of some kind (provided you have the necessary materials) or not. The constructed robot starts to produce in the next minute, and in case it is constructed, the materials needed for its construction are subtracted from the existing ones in the state. It is also possible to decide not to build anything in that minute.
The materials are then updated by adding what the existing robots have made. This order is important, since the decision whether or not to build a robot in a state is made before the materials are updated.
The goal is to know what is the maximum amount of $D$, $j=3$ type material that can be manufactured after $T= 24$ minutes.

## Data

- $n$: number of robots
- Let $mt(i,j), \i,j \in[0,n)$ be the amount of material $j$ needed to build a robot of type $i$. We will use the notation $mt(i)$ to denote the material needed to build the $i$ robot. Ex: 

$$ mt = [[4,0,0,0],[2,0,0,0],[3,14,0,0],[3,0,7,0]] $$

## Example

We design a graph whose vertices are of type $Vr$ and whose edges are of type $Ar$:

### Vr type: Properties

- $r(i)$: List, number of robots of type $i$, basic property, $$forall_{i=0}^{n-1} r(i) \geq 0$.
- $m(i)$: List, amount of material of type $i$, basic property, $$forall_{i=0}^{n-1} m(i) \geq 0$$.
- $t$: Integer, elapsed time, basic property, $0 \le t \le T$.

## Ar type

It is an enumerated type that takes the values $Ar = ${-1,0,...n-1}$.  The action $a$ means to create a robot of type $a$ if $a = 0$ and $-1$ if no robots are created. For a robot to be created there must be enough material. 

## Model

$$max \ (x_T).m(3) $$

$$x_0=([1,0,0,0],[0,0,0,0])$$

$$OP_{t=0|g}^T x_t$$

$$ Vr \ \ x_t,\ t∈[0,T]$$
