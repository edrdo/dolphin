package pt.lsts.nvl.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import pt.lsts.nvl.runtime.tasks.Task;
import pt.lsts.nvl.runtime.tasks.TaskExecutor;
import pt.lsts.nvl.util.Clock;
import pt.lsts.nvl.util.Debuggable;

public final class Environment implements Debuggable {

  private static Environment INSTANCE;

  public static Environment create(Platform platform) {
    if (INSTANCE != null) {
      throw new EnvironmentException("Runtime has already been created");
    } 
    INSTANCE = new Environment(platform);
    return INSTANCE;
  }

  public static Environment getInstance() {
    if (INSTANCE == null) {
      throw new EnvironmentException("Runtime has not been created");
    }
    return INSTANCE;
  }

  private final Platform platform;
  private final NodeSet boundVehicles;
  
  private Environment(Platform p) {
    platform = p;
    boundVehicles = new NodeSet();
  }



  public Platform getPlatform() {
    return platform;
  }


  public void run(Task task) {
    NodeSet available = platform.getConnectedVehicles();

    Map<Task,List<Node>> allocation = new HashMap<>();

    if (task.allocate(available, allocation) == false) {
      throw new EnvironmentException("No vehicles to run task!");
    }
    TaskExecutor executor = task.getExecutor();
    executor.initialize(allocation);
    executor.start();
    while (executor.getState() != TaskExecutor.State.COMPLETED) {
      executor.step();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  

  public NodeSet select(List<NodeFilter> reqList) {

    NodeSet available = platform.getConnectedVehicles();
    available.removeAll(boundVehicles);
    
    d("Available vehicles: %d", available.size());

    for (Node v : available) {
      d("  id=%s type=%s", v.getId(), v.getType());
    }

    NodeSet set = new NodeSet();
    for (NodeFilter req : reqList) {
      d("Matching requirement: %s", req.toString());
      Optional<Node> ov = 
          available.stream()
          .filter(v -> !set.contains(v) && req.matchedBy(v))
          .findFirst();
      if (! ov.isPresent()) {
        d("Requirement was not met!");
        return NodeSet.EMPTY;
      }
      d("Requirement met by vehicle %s", ov.get().getId());
      set.add(ov.get());
    }
    boundVehicles.addAll(set);
    return set;
  }

  public NodeSet select(List<NodeFilter> reqList, double timeout) {
    double startTime = Clock.now();
    d("Performing selection with timeout %f", timeout);
    double delayTime = Math.max(1.0,  timeout * 0.1);
    NodeSet set = NodeSet.EMPTY;

    while (true) {
      set = select(reqList);
      if (set != NodeSet.EMPTY || Clock.now() - startTime >= timeout) {
        break;
      }
      pause(delayTime);
    } 
    return set;
  }
  
  public void release(NodeSet set) {
    boundVehicles.removeAll(set);
  }
  
  public void pause(double time) {
    try {
      Thread.sleep(Math.round(time * 1e+03));
    }
    catch (InterruptedException e) {
      throw new EnvironmentException(e);
    }
  }
}
