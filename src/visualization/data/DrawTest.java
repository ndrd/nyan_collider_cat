/*
 Computational geometry Homework 1
 Spring 2008.
 Melkman's algorithm to compute comnvex hull of a simple polygon.
 Group:
    Gowri Somanath
    Feng Li
    Thomas St.John
 
 The startup code was taken from java demo DrawTest for applets.
 The parts of code retained from demo are for the mouse events and panel creation.
 
 */


import java.awt.event.*;
import java.awt.*;
import java.applet.*;

import java.util.Vector;

public class DrawTest extends Applet
{
    DrawPanel panel;
    DrawControls controls;
   
    public void init()
    {
        setLayout(new BorderLayout());
        panel = new DrawPanel();
        controls = new DrawControls(panel);
    
      
        add("Center", panel);
        add("South",controls);

    }
    
    public void destroy()
    {
        remove(panel);
        remove(controls);
    }
    
    public static void main(String args[])
    {
        Frame f = new Frame("DrawTest");
        DrawTest drawTest = new DrawTest();
        drawTest.init();
        drawTest.start();
        
        f.add("Center", drawTest);
        f.setSize(500, 500);
        f.show();
    }
    public String getAppletInfo()
    {
        return "A simple drawing program.";
    }
}

class DrawPanel extends Panel implements MouseListener, MouseMotionListener
{
    public boolean MAKEHULL = false;
    public static final int POINTS = 1;
    public static final int MOVEPNT = 2;
    public static final int ADDPNT = 3;
    public static final int HORIZ = 4;
    public static final int VERT = 5;
    
    public static final int CHAIN = 6;
    public static final int CLOSE = 7;
    
    int	   mode = POINTS;
    int n;
    public int FLAG = ADDPNT;
    public int MVE = HORIZ;
    int dr=0;
    
    Point [] vert=new Point[100]; //the entered verticies
    Point [] D=new Point[200];	//the deque
    Point [] H=new Point[100];	//the computed hull
    int hn=0;			//no. of verts in the hull
    
    int x1,y1;
    
    int sel=-1,selint=-1;
    Polygon poly;
    Point intp=new Point();
    Point p=new Point();
    
    
    public DrawPanel()
    {
        setBackground(Color.white);
        addMouseMotionListener(this);
        addMouseListener(this);
        n=-1;
        poly=new Polygon();
    }
    
    public void setDrawMode(int mode)
    {
        switch (mode)
        {
            case POINTS:
                this.mode = mode;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    
    public void mouseDragged(MouseEvent e)
    {
        e.consume();
        x1=e.getX();
        y1=e.getY();
        
        if(FLAG==MOVEPNT && sel>=0)
        {
            dr++;
            if (MVE==HORIZ)
                vert[sel].x=x1;
            else
                vert[sel].y=y1;
            
        }
        repaint();
    }
    
    public void mouseMoved(MouseEvent e) {    }
    
    public int findNearest(int x,int y)
    {
        int nr=-1;
        int err=9999,er;
        
        for(int i=0;i<=n;i++)
        {
            er=((vert[i].x-x)*(vert[i].x-x))+((vert[i].y-y)*(vert[i].y-y));
            if(er<err)
            {
                nr=i;
                err=er;
            }
        }
        return nr;
    }
    
    public void mousePressed(MouseEvent e)
    {
        //e.consume();
        if(FLAG==MOVEPNT)
        {
            x1=e.getX();
            y1=e.getY();
            
            if(n<0)
                return;
            sel=findNearest(x1,y1);
        }
        
    }
    
    public void mouseReleased(MouseEvent e)
    {
        e.consume();
        x1 = e.getX();
        y1 = e.getY();
        if (FLAG!=ADDPNT)
            return;
        n++;
        vert[n]=new Point(x1,y1);
        
        repaint();
        
    }
    
    public void mouseEntered(MouseEvent e) {    }
    
    public void mouseExited(MouseEvent e) {    }
    
    public void mouseClicked(MouseEvent e) {    }
    
    
    public void paint(Graphics g)
    {
        
        Rectangle rct = getBounds();
        g.setColor(Color.black);
        g.drawRect(10, 15, rct.width-50, rct.height-50);
      
        // DEBUG
        //if(sel!=-1)
        //    g.drawString("selected "+vert[sel].x+" "+vert[sel].y+" clicked "+x1+" "+y1,10,10);
        
        boolean intersect=checkIntersect();
        if(intersect)
        {
            g.drawString("Polygon is NOT simple. Click Remove Point button to remove last entered point.",10,10);
            mode=CHAIN;
        }
        
        
        int r=2;
            if(MAKEHULL)
            {
                g.setColor(Color.black);
                g.drawPolygon(poly);
                g.setColor(Color.yellow);
                g.fillPolygon(poly);
             
            }
        for (int i=0; i <= n; i++)
        {
            g.setColor(Color.red);
            
            g.drawOval(vert[i].x-r, vert[i].y-r, 2*r, 2*r);
            g.fillOval(vert[i].x-r, vert[i].y-r, 2*r, 2*r);
            if((mode==CHAIN || mode==CLOSE) && i<n)
            {
                if(intersect && i==selint)
                {
                    g.setColor(Color.green);
                    g.drawOval(intp.x-r, intp.y-r, 2*r, 2*r);
                    g.fillOval(intp.x-r, intp.y-r, 2*r, 2*r);
                    g.drawOval(p.x-r, p.y-r, 2*r, 2*r);
                    g.fillOval(p.x-r, p.y-r, 2*r, 2*r);
                    
                    
                }
                else
                    g.setColor(Color.red);
                
                g.drawLine(vert[i].x,vert[i].y,vert[i+1].x,vert[i+1].y);
            }
            
            if(MAKEHULL)
            {
                g.setColor(Color.black);
                for(int j=0;j<hn;j++)
                    g.drawLine(H[j].x,H[j].y,H[j+1].x,H[j+1].y);
            }
            
            
        }
        
        if(mode==CLOSE)
            g.drawLine(vert[n].x,vert[n].y,vert[0].x,vert[0].y);
        
        
    }
    //clear entered points
    public void clear()
    {
        for (int i=0;i<=n;i++)
            vert[i]=null;
        n=-1;
        sel=-1;
        selint=-1;
        mode=POINTS;
        resetpoly();
        
     }
    //resets the hull
    public void resetpoly()
    {
        poly=null;
        poly=new Polygon();
        //poly.reset();
        MAKEHULL=false;
        for (int i=0;i<=hn;i++)
            H[i]=null;
        hn=-1;
        repaint();
    }
    
    //removes last point entered
    public void remove()
    {
        vert[n]=null;
        n--;
        resetpoly();
        
    }

	//check if p2 is left of line formed by p0-p1    
    public float isLeft( Point P0, Point P1, Point P2 )
    {
        float chk= (P1.x - P0.x)*(P2.y - P0.y) - (P2.x - P0.x)*(P1.y - P0.y);
return chk;
    }
    
    //to check if the newly added edge intersects any other edge and makes the polygon complex
    public boolean checkIntersect()
    {
        
        if (n<3) return false;
        Point p1=new Point(vert[n-1]);
        Point p2=new Point(vert[n]);
        Point p3=new Point();
        Point p4=new Point();
        
        float a,b,c,d,denom,e,f,ua,ub;
        int junk;
        float x1,y1,x2,y2;
        for (int i=0;i<n-2;i++)
        {
            p3=vert[i];
            p4=vert[i+1];
            if (isLeft(p3,p4,p1)>0 && isLeft(p3,p4,p2)>0)
                junk=10; //no
            else if(isLeft(p1,p2,p3)>0 && isLeft(p1,p2,p4)>0)
                junk=10;//no
            else
            {
                selint=i;
                a=(p4.x-p3.x)*(p1.y-p3.y);
                b=(p4.y-p3.y)*(p1.x-p3.x);
                c=(p4.y-p3.y)*(p2.x-p1.x);
                d=(p4.x-p3.x)*(p2.y-p1.y);
                ua=(a-b)/(c-d);
                
                a=(p2.x-p1.x)*(p1.y-p3.y);
                b=(p2.y-p1.y)*(p1.x-p3.x);
                c=(p4.y-p3.y)*(p2.x-p1.x);
                d=(p4.x-p3.x)*(p2.y-p1.y);
                ub=(a-b)/(c-d);
                
                if(ua>=0.0f && ua<=1.0f && ub>=0.0f && ub<=1.0f)
                    return true;
                
                
            }
            
        }
        return false;
    }
    
    
    //function  to compute the hull H from verticies vert using Melkman's algorithm.
    public void makeHull() throws InterruptedException
    {
        MAKEHULL=true;
        n++;
        int bot = n-2, top = bot+3;
        D[bot] =new Point(vert[2]);
        D[top] = new Point(vert[2]);
        if (isLeft(vert[0], vert[1], vert[2]) > 0) {
            D[bot+1] = new Point(vert[0]);
            D[bot+2] = new Point(vert[1]);
        }
        else {
            D[bot+1] = new Point(vert[1]);
            D[bot+2] = new Point(vert[0]);
        }
        
        
        for (int i=3; i < n; i++)
        {
            if ((isLeft(D[bot], D[bot+1], vert[i]) > 0) &&
            (isLeft(D[top-1], D[top], vert[i]) > 0) )
                continue;
            
            while (isLeft(D[bot], D[bot+1], vert[i]) <= 0)
                ++bot;
            
            D[--bot] = new Point(vert[i]);
            
            
            while (isLeft(D[top-1], D[top], vert[i]) <= 0)
                --top;
            D[++top] = new Point(vert[i]);
        }
        
        
        int h;
        for (h=0; h <= (top-bot); h++)
        {
            H[h] = new Point(D[bot + h]);
            poly.addPoint(H[h].x,H[h].y);
            D[bot+h]=null;
        }
        
        n--;
        hn= h-1;
    }
    
    
}

//************************************************************************

class DrawControls extends Panel implements ActionListener,ItemListener
{
    DrawPanel target;
    
    public DrawControls(DrawPanel target)
    {
        this.target = target;
        Button clear=new Button("Clear");
        add(clear);
        
        
        
        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox horiz=new Checkbox("Horizontal", cbg, true);
        Checkbox vert=new Checkbox("Vertical", cbg, false);
        CheckboxGroup cbg2 = new CheckboxGroup();
        Checkbox move=new Checkbox("Move point", cbg2, false);
        Checkbox addp=new Checkbox("Add point", cbg2, true);
        add(move);
        add(horiz);
        add(vert);
        add(addp);
        
        Button chain=new Button("Chain");
        add(chain);
        
        Button close=new Button("Close");
        add(close);
        
        Button startalgo=new Button("Run Melkman");
        add(startalgo);
        
        Button remove=new Button("Remove point");
        add(remove);
        
        
        setLayout(new FlowLayout());
        setBackground(Color.lightGray);
        target.setForeground(Color.red);
        clear.addActionListener(this);
        startalgo.addActionListener(this);
        remove.addActionListener(this);
        horiz.addItemListener(this);
        vert.addItemListener(this);
        move.addItemListener(this);
        addp.addItemListener(this);
        chain.addActionListener(this);
        close.addActionListener(this);
        
    }
    
    public void paint(Graphics g)
    {
        Rectangle r = getBounds();
        g.setColor(Color.lightGray);
        g.draw3DRect(0, 0, r.width, r.height, false);
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        try
        {
            if(evt.getActionCommand()=="Clear")
                target.clear();
            if(evt.getActionCommand()=="Remove point")
                target.remove();
            
            else if(evt.getActionCommand()=="Run Melkman")
            {target.makeHull();}
            else if(evt.getActionCommand()=="Chain")
            {target.mode=target.CHAIN;}
            else if(evt.getActionCommand()=="Close")
            {target.mode=target.CLOSE;}
            target.repaint();
        }
        catch (Exception e)
        {}
    }
    
    public void itemStateChanged(ItemEvent ie)
    {
        if(ie.getItem()=="Horizontal")
            target.MVE=target.HORIZ;
        else if(ie.getItem()=="Vertical")
            target.MVE=target.VERT;
        else if(ie.getItem()=="Move point")
        {
            if (target.MAKEHULL)
                target.resetpoly();
            target.FLAG=target.MOVEPNT;
        }
        else if(ie.getItem()=="Add point")
        {
            if (target.MAKEHULL)
                target.resetpoly();
            target.FLAG=target.ADDPNT;
        }
        target.repaint();
        
    }
    
}




