package DSCoinPackage;

import java.util.*;
import java.util.Arrays;
import HelperClasses.*;

public class Members{

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSobj) {
    Members current = this;
    Pair<String, TransactionBlock> p = current.mycoins.get(0);
    List<Pair<String, TransactionBlock>> newcoins = new ArrayList<Pair<String, TransactionBlock>>();
    int b = 1;
    while(b<current.mycoins.size()){
      Pair<String, TransactionBlock> pn = new Pair<String, TransactionBlock>(this.mycoins.get(b).first, this.mycoins.get(b).second);
      newcoins.add(pn);
      b=b+1;
    }
    this.mycoins = newcoins;
    Transaction tobj = new Transaction();
    tobj.coinID = p.first;
    tobj.coinsrc_block = p.second;
    tobj.Source = current;
    Members dest = new Members();
    Members[] mlist = DSobj.memberlist;
    for(int i=0; i<mlist.length; i++){
      if(mlist[i].UID.equals(destUID)){
        dest = mlist[i];
        break;
      }
    } 
    tobj.Destination = dest;
    Transaction[] intrans = current.in_process_trans;
    if(intrans==null){
      intrans = new Transaction[100];
    }
    for(int j=0; j<intrans.length; j++){
      if(intrans[j]==null){
        intrans[j]=tobj;
        break;
      }
    }
    DSobj.pendingTransactions.AddTransactions(tobj);
  }

  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {
    BlockChain_Honest current = DSObj.bChain;
    TransactionBlock curr = current.lastBlock;
    List<TransactionBlock> blist = new ArrayList<TransactionBlock>();
    boolean b = false;
    int n = 0;
    while(b==false && curr!=null){
      Transaction[] tarray = curr.trarray;
      for(int i=0; i<tarray.length; i++){
        if(tobj.coinsrc_block==null){
          if(tarray[i].coinID.equals(tobj.coinID) &&
            tarray[i].Source.UID.equals(tobj.Source.UID) &&
            tarray[i].Destination.UID.equals(tobj.Destination.UID)){
            n=i;
            b=true;
          }
        }
        else{
          if(tarray[i].coinID.equals(tobj.coinID) &&
            tarray[i].Source.UID.equals(tobj.Source.UID) &&
            tarray[i].Destination.UID.equals(tobj.Destination.UID) &&
            tarray[i].coinsrc_block.trsummary.equals(tobj.coinsrc_block.trsummary)){
            n=i;
            b=true;
          }
        }        
      }
      blist.add(curr);
      curr=curr.previous;
    }
    if(b==false){
      throw new MissingTransactionException();
    }
    else{
      TransactionBlock coinsrc = blist.get(blist.size()-1);
      List<Pair<String, String>> l1 = new ArrayList<Pair<String, String>>();
      MerkleTree mtree = coinsrc.Tree;
      List<TreeNode> nlist = mtree.trlist;
      TreeNode curr1 = nlist.get(n);
      while(curr1.parent!=null){
        curr1=curr1.parent;
        Pair<String, String> p1 = new Pair<String, String>(curr1.left.val, curr1.right.val);
        l1.add(p1);
      }
      Pair<String, String> p11 = new Pair<String, String>(curr1.val,null);
      l1.add(p11);
      List<Pair<String, String>> l2 = new ArrayList<Pair<String, String>>();
      if(coinsrc.previous==null){
        Pair<String, String> p22 = new Pair<String, String>("DSCoin", null);
        l2.add(p22);
      }
      else{
        Pair<String, String> p22 = new Pair<String, String>(coinsrc.previous.dgst, null);
        l2.add(p22);
      }
      for(int j=blist.size()-1; j>-1; j--){
        TransactionBlock temp = blist.get(j);
        if(temp.previous==null){
          Pair<String, String> p2 = new Pair<String, String>(temp.dgst, "DSCoin" + "#" + temp.trsummary + "#" + temp.nonce);
          l2.add(p2);
        }
        else{
          Pair<String, String> p2 = new Pair<String, String>(temp.dgst, temp.previous.dgst + "#" + temp.trsummary + "#" + temp.nonce);
          l2.add(p2);
        }
      }
      Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalPair = new Pair<List<Pair<String, String>>, List<Pair<String, String>>>(l1, l2);
      Transaction[] inprtrans = tobj.Source.in_process_trans;
      if(inprtrans==null){
        inprtrans = new Transaction[100];
      }
      Transaction[] temporary = new Transaction[100];
      int k = 0;int d = 0;
      while(k<inprtrans.length){
        if(inprtrans[k]!=null && !inprtrans[k].equals(tobj)){
          temporary[d]=inprtrans[k];
          d=d+1;
        }
        k=k+1;
      }
      tobj.Source.in_process_trans=temporary;
      Members des = tobj.Destination;
      List<Pair<String, TransactionBlock>> descoins = des.mycoins;
      Pair<String, TransactionBlock> p3 = new Pair<String, TransactionBlock>(tobj.coinID, coinsrc);
      int c = 0;
      if(descoins.size()==0){
        descoins.add(p3);
      }
      else{
        for(int m=0; m<descoins.size(); m++){
          if(Integer.valueOf(descoins.get(m).first)>Integer.valueOf(tobj.coinID)){
            c=m;
            break;
          }
        }
        if(c==0 && Integer.valueOf(descoins.get(descoins.size()-1).first)<Integer.valueOf(tobj.coinID)){
          descoins.add(p3);
        }    
        else{
          descoins.add(c, p3);
        }
      }
      return finalPair;
    }  
  }

  public static boolean isvalid(Transaction t, TransactionBlock tB){
    Transaction[] list = {t};
    TransactionBlock tb = new TransactionBlock(list);
    tb.previous =  tB;
    boolean b = false;
    if(tb.checkTransaction(t)==true){
      b=true;
    }
    tb.previous=null;
    return b;
  }

  public static boolean isnotdouble(Transaction t, Transaction[] tarray, int l){
    boolean b = true;
    if(l!=0){
      for(int i=0; i<l; i++){
        if(tarray[i].equals(t)){
          b=false;
          break;
        }
      }
    }
    return b;
  }

  public void MineCoin(DSCoin_Honest DSObj) {
    TransactionQueue pending = DSObj.pendingTransactions;
    int n = DSObj.bChain.tr_count;
    Transaction[] tarray = new Transaction[n];
    int i=0;
    while(i<n-1){
      try{
        Transaction temp = pending.RemoveTransaction();
        if(isvalid(temp,DSObj.bChain.lastBlock)==true  && isnotdouble(temp,tarray,i)==true){
          tarray[i]=temp;
          i=i+1;
        }        
      }
      catch(Exception e){
        System.out.println("Empty Queue");
      }
    } 
    Transaction minerRewardTransaction = new Transaction();  
    DSObj.latestCoinID = String.valueOf(Integer.valueOf(DSObj.latestCoinID)+1);
    minerRewardTransaction.coinID = DSObj.latestCoinID;
    minerRewardTransaction.Source = null;
    minerRewardTransaction.Destination = this;
    minerRewardTransaction.coinsrc_block = null;
    tarray[n-1]=minerRewardTransaction;
    TransactionBlock tB = new TransactionBlock(tarray);
    DSObj.bChain.InsertBlock_Honest(tB);
    List<Pair<String, TransactionBlock>> minercoins = this.mycoins;
    Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(DSObj.latestCoinID, tB);
    int c = 0;
    if(minercoins.size()==0){
      minercoins.add(p);
    }
    else{
      for(int m=0; m<minercoins.size(); m++){
        if(Integer.valueOf(minercoins.get(m).first)>Integer.valueOf(DSObj.latestCoinID)){
          c=m;
          break;
        }
      }
      if(c==0 && Integer.valueOf(minercoins.get(minercoins.size()-1).first)<Integer.valueOf(DSObj.latestCoinID)){
        minercoins.add(p);
      }    
      else{
        minercoins.add(c, p);
      }
    }      
  }

  public void MineCoin(DSCoin_Malicious DSObj) {
    BlockChain_Malicious bchain = DSObj.bChain;
    TransactionBlock validblock = bchain.FindLongestValidChain();
    TransactionQueue pending = DSObj.pendingTransactions;
    int n = DSObj.bChain.tr_count;
    Transaction[] tarray = new Transaction[n];
    int i=0;
    while(i<n-1){
      try{
        Transaction temp = pending.RemoveTransaction();
        if(isvalid(temp,validblock)==true && isnotdouble(temp,tarray,i)==true){
          tarray[i]=temp;
          i=i+1;
        }
      }
      catch(Exception e){
        System.out.println("Empty Queue");
      }
    }
    Transaction minerRewardTransaction = new Transaction();  
    DSObj.latestCoinID = String.valueOf(Integer.valueOf(DSObj.latestCoinID)+1);
    minerRewardTransaction.coinID = DSObj.latestCoinID;
    minerRewardTransaction.Source = null;
    minerRewardTransaction.Destination = this;
    minerRewardTransaction.coinsrc_block = null;
    tarray[n-1]=minerRewardTransaction;
    TransactionBlock tB = new TransactionBlock(tarray);
    DSObj.bChain.InsertBlock_Malicious(tB);
    List<Pair<String, TransactionBlock>> minercoins = this.mycoins;
    Pair<String, TransactionBlock> p = new Pair<String, TransactionBlock>(DSObj.latestCoinID, tB);
    int c=0;
    if(minercoins.size()==0){
      minercoins.add(p);
    }
    else{
      for(int m=0; m<minercoins.size(); m++){
        if(Integer.valueOf(minercoins.get(m).first)>Integer.valueOf(DSObj.latestCoinID)){
          c=m;
          break;
        }
      }
      if(c==0 && Integer.valueOf(minercoins.get(minercoins.size()-1).first)<Integer.valueOf(DSObj.latestCoinID)){
        minercoins.add(p);
      }    
      else{
        minercoins.add(c, p);
      }
    }      
  }  
    
}