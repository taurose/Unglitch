    // Unglitch Start
    // http://www.cygnus-software.com/papers/comparingfloats/comparingfloats.htm
    public static boolean isGreaterThanWithEpsilon(double a, double b, int maxUlps)
    {
        long aBits = Double.doubleToRawLongBits(a);
        // Make aBits lexicographically ordered as a twos-complement
        if (aBits < 0)
            aBits = 0x8000000000000000L - aBits;
        // Make bBits lexicographically ordered as a twos-complement
        long bBits = Double.doubleToRawLongBits(b);
        if (bBits < 0)
            bBits = 0x8000000000000000L - bBits;
        return (aBits - bBits) >= -maxUlps;
    }
    // --


    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateXOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY)
        {
            if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ)
            {
                double var4;

                // Unglitch Change Start
                /*if (par2 > 0.0D && par1AxisAlignedBB.maxX <= this.minX)
                {
                    var4 = this.minX - par1AxisAlignedBB.maxX;

                    if (var4 < par2)
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D && par1AxisAlignedBB.minX >= this.maxX)
                {
                    var4 = this.maxX - par1AxisAlignedBB.minX;

                    if (var4 > par2)
                    {
                        par2 = var4;
                    }
                }*/

                if (par2 > 0.0D)
                {
                    var4 = this.minX - par1AxisAlignedBB.maxX;

                    if (var4 < par2 && isGreaterThanWithEpsilon(this.minX, par1AxisAlignedBB.maxX, 100))
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D)
                {
                    var4 = this.maxX - par1AxisAlignedBB.minX;

                    if (var4 > par2 && isGreaterThanWithEpsilon(par1AxisAlignedBB.minX, this.maxX, 100))
                    {
                        par2 = var4;
                    }
                }

                // --

                return par2;
            }
            else
            {
                return par2;
            }
        }
        else
        {
            return par2;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateYOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX)
        {
            if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ)
            {
                double var4;

                // Unglitch Change Start
                /*if (par2 > 0.0D && par1AxisAlignedBB.maxY <= this.minY)
                {
                    var4 = this.minY - par1AxisAlignedBB.maxY;

                    if (var4 < par2)
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D && par1AxisAlignedBB.minY >= this.maxY)
                {
                    var4 = this.maxY - par1AxisAlignedBB.minY;

                    if (var4 > par2)
                    {
                        par2 = var4;
                    }
                }*/


                if (par2 > 0.0D)
                {
                    var4 = this.minY - par1AxisAlignedBB.maxY;

                    if (var4 < par2 && isGreaterThanWithEpsilon(this.minY, par1AxisAlignedBB.maxY, 100))
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D)
                {
                    var4 = this.maxY - par1AxisAlignedBB.minY;

                    if (var4 > par2 && isGreaterThanWithEpsilon(par1AxisAlignedBB.minY, this.maxY, 100))
                    {
                        par2 = var4;
                    }
                }

                // --

                return par2;
            }
            else
            {
                return par2;
            }
        }
        else
        {
            return par2;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateZOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX)
        {
            if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY)
            {
                double var4;

                // Unglitch Change Start
                /*if (par2 > 0.0D && par1AxisAlignedBB.maxZ <= this.minZ)
                {
                    var4 = this.minZ - par1AxisAlignedBB.maxZ;

                    if (var4 < par2)
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D && par1AxisAlignedBB.minZ >= this.maxZ)
                {
                    var4 = this.maxZ - par1AxisAlignedBB.minZ;

                    if (var4 > par2)
                    {
                        par2 = var4;
                    }
                }*/

                if (par2 > 0.0D)
                {
                    var4 = this.minZ - par1AxisAlignedBB.maxZ;

                    if (var4 < par2 && isGreaterThanWithEpsilon(this.minZ, par1AxisAlignedBB.maxZ, 100))
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D)
                {
                    var4 = this.maxZ - par1AxisAlignedBB.minZ;

                    if (var4 > par2 && isGreaterThanWithEpsilon(par1AxisAlignedBB.minZ, this.maxZ, 100))
                    {
                        par2 = var4;
                    }
                }

                // --

                return par2;
            }
            else
            {
                return par2;
            }
        }
        else
        {
            return par2;
        }
    }

