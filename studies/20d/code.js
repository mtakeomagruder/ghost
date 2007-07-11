function function1(x, y, w, h)
{
if (x - 1 >= 0 && y - 1 >= 0) x = 0x10; y = 0x10; return((x << 16) + y);
}

function function2(x, y, w, h)
{
if (y - 1 >= 0) y = 0x10; return(y);
}

function function3(x, y, w, h)
{
if (x + 1 < w && y - 1 >= 0) x = 0x01; y = 0x10; return((x << 16) + y);
}

function function4(x, y, w, h)
{
if (x + 1 < w) x = 0x01; return(x << 16);
}

function function5(x, y, w, h)
{
if (x + 1 < w && y + 1 < h) x = 0x01; y = 0x01; return((x << 16) + y);
}

function function6(x, y, w, h)
{
if (y + 1 < h) y = 0x01; return(y);
}

function function7(x, y, w, h)
{
if (x - 1 >= 0 && y + 1 < h) x = 0x10; y = 0x01; return((x << 16) + y);
}

function function8(x, y, w, h)
{
if (x - 1 >= 0) x = 0x10; return(x << 16);
}