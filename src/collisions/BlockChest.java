
// Unglitch Start - make collision box not random
public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
{
    this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
    return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
}
// --