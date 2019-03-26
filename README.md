# StandardEarth
A Java package that can provide gravity and atmosphere data based on standard Earth approximations.

Although accurate enough for my current purposes. There are areas that can be improved.

# Sources
Standard Atmosphere 1976:
    
    https://ntrs.nasa.gov/search.jsp?R=19770009539 

Gravity:
    
    https://www.mathworks.com/matlabcentral/fileexchange/8359-ellipsoidal-gravity-vector
    https://dspace.cvut.cz/bitstream/handle/10467/68382/F3-BP-2017-Lustig-Matyas-Modeling%20of%20Launch%20Vehicle%20during%20the%20Lift-off%20Phase%20in%20Atmosphere.pdf

# Possible Improvements
- More interpolation points for pressure calculation in Layer 1000.
    - These can be found in the standard atmosphere 1976
- Higher oreder gravity approximation
- Better return types so it is easier to integrate into other projects
