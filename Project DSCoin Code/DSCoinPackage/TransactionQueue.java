package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;

  public void AddTransactions (Transaction transaction) {
    TransactionQueue current = this;
    Transaction t = transaction;
    if(current.numTransactions==0){
      current.firstTransaction=t;
      current.lastTransaction=t;
      t.previous=null;      
    }
    else{
      Transaction curr=current.lastTransaction;
      curr.next=t;
      t.previous=curr;
      current.lastTransaction=t;
    }
    t.next=null;
    current.numTransactions=current.numTransactions+1;
  }
  
  public Transaction RemoveTransaction () throws EmptyQueueException {
    TransactionQueue current=this;
    try{
      Transaction curr=current.firstTransaction;      
      current.firstTransaction=curr.next;
      curr.next=null;
      current.numTransactions=current.numTransactions-1;
      if(current.firstTransaction!=null){
        current.firstTransaction.previous=null;
      }
      return curr;
    }  
    catch(Exception e){
      throw new EmptyQueueException();
    } 
  }

  public int size() {
    TransactionQueue current=this;
    return current.numTransactions;
  }

}